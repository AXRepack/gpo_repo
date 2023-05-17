package tomskasu.sancor.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import tomskasu.sancor.entity.Roles;
import tomskasu.sancor.entity.UserEntity;
import tomskasu.sancor.entity.Worker;
import tomskasu.sancor.repo.RoleRepo;
import tomskasu.sancor.repo.UserEntityRepo;
import tomskasu.sancor.repo.WorkerRepo;

import java.util.Collections;

@Controller
@RequestMapping("/auth")
public class RegisterController {
    final
    AuthenticationManager authenticationManager;
    final
    WorkerRepo workerRepo;
    final
    RoleRepo roleRepo;
    final
    PasswordEncoder passwordEncoder;
    final
    UserEntityRepo userEntityRepo;
    @Autowired
    public RegisterController(AuthenticationManager authenticationManager, WorkerRepo workerRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder, UserEntityRepo userEntityRepo) {
        this.authenticationManager = authenticationManager;
        this.workerRepo = workerRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
        this.userEntityRepo = userEntityRepo;
    }


    @PostMapping("login")
    public ModelAndView login(@RequestParam String username, @RequestParam String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ModelAndView("redirect:/profile");
    }

    @GetMapping("login")
    public String loginView() {
        return "login";
    }


    @GetMapping("register")
    public String register(Model model) {
        model.addAttribute("response", "Register page");
        model.addAttribute("user", new UserEntity());

        return "register";
    }

    @PostMapping("register")
    public String registerUser(@ModelAttribute("user") UserEntity user,
                               Model model,
                               @RequestParam String surname,
                               @RequestParam String patronymic,
                               @RequestParam String name) {

    if (userEntityRepo.existsByUsername(user.getUsername())) {
        model.addAttribute("response", "User already exists");
        return "register";
    }



    user.setPassword(passwordEncoder.encode(user.getPassword()));
    Roles roles = roleRepo.findByName("USER").get();
    user.setRoles(Collections.singletonList(roles));
    user.getWorker().setName(surname + " " + name + " " + patronymic);
    workerRepo.save(user.getWorker());
    userEntityRepo.save(user);

    model.addAttribute("response", "User successfully signed up");
    return "register";
    }

}
