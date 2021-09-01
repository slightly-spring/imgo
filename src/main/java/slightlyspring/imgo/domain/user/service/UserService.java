package slightlyspring.imgo.domain.user.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import slightlyspring.imgo.domain.user.domain.User;
import slightlyspring.imgo.domain.user.dto.UserProfile;
import slightlyspring.imgo.domain.user.repository.UserRepository;

import java.util.Optional;


@Service
class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public UserProfile getUserProfile(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        UserProfile userProfile = modelMapper.map(user, UserProfile.class);

        return userProfile;
    }

}
