package rs.raf.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rs.raf.demo.model.User;
import rs.raf.demo.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IService<User,Long>,  UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User myUser = this.userRepository.findUserByEmail(username);
        if(myUser == null) {
            throw new UsernameNotFoundException("User name "+username+" not found");
        }

        return new org.springframework.security.core.userdetails.User(myUser.getEmail(), myUser.getPassword(), new ArrayList<>());
    }

    @Override
    public  Optional<User> findById(Long userId) {



        return userRepository.findById(userId);
    }



    @Override
    public void updateEntity(User userId) {

    }

    @Override
    public User createEntity(User user) {


        return this.userRepository.save(user);

    }

    @Override
    public void deleteEntity(Long userId) {
        this.userRepository.deleteById(userId);
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    public Optional<User> findUserByEmail(String email) {
        return Optional.ofNullable(userRepository.findUserByEmail(email));
    }

}
