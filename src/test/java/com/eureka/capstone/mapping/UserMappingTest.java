//package com.eureka.capstone.mapping;
//
//import com.eureka.capstone.domain.user.Role;
//import com.eureka.capstone.domain.user.RoleEnum;
//import com.eureka.capstone.domain.user.User;
//import com.eureka.capstone.dto.UserDto;
//import com.eureka.capstone.repository.RoleRepository;
//import com.eureka.capstone.service.RoleService;
//import com.eureka.capstone.service.impl.RoleServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//import static org.mockito.BDDMockito.given;
//
//@SpringBootTest
//public class UserMappingTest {
//
//    static RoleRepository roleRepository;
//
//    static RoleService roleService;
//
//    @Autowired
//    private UserMapper userMapper;
//
//    @BeforeEach
//    public void setup() {
//        roleRepository = Mockito.mock(RoleRepository.class);
//        roleService = new RoleServiceImpl(roleRepository);
//        userMapper.roleService = roleService;
//    }
//
//    @Test
//    public void userRegistrationDtoToUser() {
//        UserDto userDto = new UserDto();
//        userDto.setFullName("James Smith");
//        userDto.setPassword("12345678");
//        userDto.setUsername("user012364");
//        userDto.setEmail("jamie.smith@hotmail.com");
//        userDto.setPhone("+374356985478");
//
//        Role role = new Role();
//        Optional<Role> roleOptional = Optional.of(role);
//        given(roleRepository.getByRoleName(RoleEnum.USER_ROLE)).willReturn(roleOptional);
//
//        User user = userMapper.toEntity(userDto);
//
//        assertEquals(userDto.getFullName(), user.getFullName());
//        assertEquals(userDto.getPassword(), user.getPassword());
//        assertEquals(userDto.getUsername(), user.getUsername());
//        assertEquals(userDto.getEmail(), user.getEmail());
//        assertEquals(userDto.getPhone(), user.getPhone());
//        assertTrue(user.getRoles().contains(role));
//    }
//}
