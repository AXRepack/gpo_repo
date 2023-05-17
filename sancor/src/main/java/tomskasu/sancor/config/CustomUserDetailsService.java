package tomskasu.sancor.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tomskasu.sancor.entity.UserEntity;
import tomskasu.sancor.repo.UserEntityRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    final
    UserEntityRepo userEntityRepo;

    public CustomUserDetailsService(UserEntityRepo userEntityRepo) {
        this.userEntityRepo = userEntityRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userEntityRepo.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
        return new User(user.getUsername(), user.getPassword(), user.getRoles());
    }
}
