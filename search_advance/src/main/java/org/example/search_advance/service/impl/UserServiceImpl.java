package org.example.search_advance.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.search_advance.dto.request.AddressDTO;
import org.example.search_advance.dto.request.UserRequestDto;
import org.example.search_advance.dto.response.PageResponse;
import org.example.search_advance.dto.response.UserBasicInfo;
import org.example.search_advance.dto.response.UserDetailResponse;
import org.example.search_advance.exception.InvalidDataException;
import org.example.search_advance.exception.ResourceNotFoundException;
import org.example.search_advance.model.Address;
import org.example.search_advance.model.User;
import org.example.search_advance.repository.UserRepository;
import org.example.search_advance.service.UserService;
import org.example.search_advance.util.UserStatus;
import org.example.search_advance.util.UserType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.example.search_advance.util.AppConst.SORT_BY;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private static final String LIKE_FORMAT = "%%%s%%";

    @Value("${app.upload-dir}")
    private String targetDir;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

    }

    @Override
    @Transactional(readOnly = true)
    public List<UserBasicInfo> getAllBasicInfo() {
        return userRepository.getBasicInfoByStatus(UserStatus.ACTIVE);
    }


    @Override
    public UserDetailResponse getUser(long userId) {
        User user = getUserById(userId);
        return UserDetailResponse.builder().id(userId).firstName(user.getFirstName()).lastName(user.getLastName()).phone(user.getPhone()).email(user.getEmail()).build();
    }


    @Override
    public long saveUser(UserRequestDto request) {
        User user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName()).dateOfBirth(request.getDateOfBirth()).gender(request.getGender()).phone(request.getPhone()).email(request.getEmail()).username(request.getUsername()).password(request.getPassword()).status(request.getStatus()).type(UserType.valueOf(request.getType().toUpperCase())).build();
        request.getAddresses().forEach(a -> user.saveAddress(Address.builder().apartmentNumber(a.getApartmentNumber()).floor(a.getFloor()).building(a.getBuilding()).streetNumber(a.getStreetNumber()).street(a.getStreet()).city(a.getCity()).country(a.getCountry()).addressType(a.getAddressType()).build()));

        userRepository.save(user);

        log.info("User has save!");

        return user.getId();
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void updateUser(long userId, UserRequestDto request) {
        User user = getUserById(userId);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setGender(request.getGender());
        user.setPhone(request.getPhone());
        if (!request.getEmail().equals(user.getEmail())) {
            // check email from database if not exist then allow update email otherwise throw exception
            user.setEmail(request.getEmail());
        }
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setStatus(request.getStatus());
        user.setType(UserType.valueOf(request.getType().toUpperCase()));
        user.setAddresses(convertToAddress(request.getAddresses()));
        userRepository.save(user);

        log.info("User updated successfully");
    }

    @Override
    public void changeStatus(long userId, UserStatus status) {
        User user = getUserById(userId);
        user.setStatus(status);
        userRepository.save(user);
        log.info("status changed");
    }

    @Override
    public void deleteUser(long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }
        userRepository.deleteById(userId);
    }

    @Override
    public String updateAvatar(Long id, MultipartFile file) {
        User user = getUserById(id);
        if (file == null || file.isEmpty()) {
            throw new InvalidDataException(" file is empty");
        }
        try {
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path targetPath = Paths.get(targetDir).resolve(filename);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            user.setAvatar(filename);
            userRepository.save(user);

            return filename;

        } catch (IOException e) {
            log.error("Upload avatar failed", e);
            throw new RuntimeException("Cannot upload avatar", e);
        }
    }

    @Override
    public boolean existUser(String email) {
        return userRepository.existsByEmail(email);
    }


    @Override
    public PageResponse<?> getAllUsersWithSortBy(int pageNo, int pageSize, String sortBy) {
        int page = 0;
        if (pageNo > 0) {
            page = pageNo - 1;
        }

        List<Sort.Order> sorts = new ArrayList<>();

        if (StringUtils.hasLength(sortBy)) {
            // firstName:asc|desc
            Pattern pattern = Pattern.compile(SORT_BY);
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()) {
                if (matcher.group(3).equalsIgnoreCase("asc")) {
                    sorts.add(new Sort.Order(Sort.Direction.ASC, matcher.group(1)));
                } else {
                    sorts.add(new Sort.Order(Sort.Direction.DESC, matcher.group(1)));
                }
            }
        }

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sorts));

        Page<User> users = userRepository.findAll(pageable);
        List<UserDetailResponse> response = users.stream().map(user -> UserDetailResponse.builder().id(user.getId()).firstName(user.getFirstName()).lastName(user.getLastName()).email(user.getEmail()).phone(user.getPhone()).build()).toList();
        return PageResponse.builder().pageNo(pageNo).pageSize(pageSize).totalPage(users.getTotalPages()).items(response).build();
    }


    @Override
    public PageResponse<?> getAllUsersAndSearchWithPagingAndSorting(int pageNo, int pageSize, String search, String sortBy) {

        StringBuilder sqlQuery = new StringBuilder("SELECT new org.example.search_advance.dto.response.UserDetailResponse(u.id, u.firstName, u.lastName, u.phone, u.email) FROM User u WHERE 1=1");
        if (StringUtils.hasLength(search)) {
            sqlQuery.append(" AND lower(u.firstName) like lower(:firstName)");
            sqlQuery.append(" OR lower(u.lastName) like lower(:lastName)");
            sqlQuery.append(" OR lower(u.email) like lower(:email)");
        }

        if (StringUtils.hasLength(sortBy)) {
            // firstName:asc|desc
            Pattern pattern = Pattern.compile(SORT_BY);
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()) {
                sqlQuery.append(String.format(" ORDER BY u.%s %s", matcher.group(1), matcher.group(3)));
            }
        }

        // Get list of users
        Query selectQuery = entityManager.createQuery(sqlQuery.toString());
        if (StringUtils.hasLength(search)) {
            selectQuery.setParameter("firstName", String.format(LIKE_FORMAT, search));
            selectQuery.setParameter("lastName", String.format(LIKE_FORMAT, search));
            selectQuery.setParameter("email", String.format(LIKE_FORMAT, search));
        }
        selectQuery.setFirstResult(pageNo);
        selectQuery.setMaxResults(pageSize);
        List<?> users = selectQuery.getResultList();

        // Count users
        StringBuilder sqlCountQuery = new StringBuilder("SELECT COUNT(*) FROM User u");
        if (StringUtils.hasLength(search)) {
            sqlCountQuery.append(" WHERE lower(u.firstName) like lower(?1)");
            sqlCountQuery.append(" OR lower(u.lastName) like lower(?2)");
            sqlCountQuery.append(" OR lower(u.email) like lower(?3)");
        }

        Query countQuery = entityManager.createQuery(sqlCountQuery.toString());
        if (StringUtils.hasLength(search)) {
            countQuery.setParameter(1, String.format(LIKE_FORMAT, search));
            countQuery.setParameter(2, String.format(LIKE_FORMAT, search));
            countQuery.setParameter(3, String.format(LIKE_FORMAT, search));
            countQuery.getSingleResult();
        }

        Long totalElements = (Long) countQuery.getSingleResult();
        log.info("totalElements={}", totalElements);

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<?> page = new PageImpl<>(users, pageable, totalElements);

        return PageResponse.builder().pageNo(pageNo).pageSize(pageSize).totalPage(page.getTotalPages()).items(users).build();
    }

    private User getUserById(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private Set<Address> convertToAddress(Set<AddressDTO> addresses) {
        Set<Address> result = new HashSet<>();
        addresses.forEach(a -> result.add(Address.builder().apartmentNumber(a.getApartmentNumber()).floor(a.getFloor()).building(a.getBuilding()).streetNumber(a.getStreetNumber()).street(a.getStreet()).city(a.getCity()).country(a.getCountry()).addressType(a.getAddressType()).build()));
        return result;
    }

    //    List<Sort.Order> orders = new ArrayList<>();
//
//        if (sorts != null) {
//        for (String sortBy : sorts) {
//            log.info("sortBy: {}", sortBy);
//            // firstName:asc|desc
//            Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");
//            Matcher matcher = pattern.matcher(sortBy);
//            if (matcher.find()) {
//                if (matcher.group(3).equalsIgnoreCase("asc")) {
//                    orders.add(new Sort.Order(Sort.Direction.ASC, matcher.group(1)));
//                } else {
//                    orders.add(new Sort.Order(Sort.Direction.DESC, matcher.group(1)));
//                }
//            }
//        }
//    }
}
