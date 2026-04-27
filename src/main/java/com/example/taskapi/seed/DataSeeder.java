package com.example.taskapi.seed;

import com.example.taskapi.models.Role;
import com.example.taskapi.repository.RoleRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements ApplicationRunner {

    private final RoleRepository roleRepository;

    public DataSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (roleRepository.findByName("ADMIN").isEmpty()) {
            Role admin = new Role();
            admin.setName("ADMIN");
            roleRepository.save(admin);
        }
        if (roleRepository.findByName("USER").isEmpty()) {
            Role user = new Role();
            user.setName("USER");
            roleRepository.save(user);
        }
    }
}