package slightlyspring.imgo.domain.user.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import slightlyspring.imgo.domain.user.domain.User;
import slightlyspring.imgo.domain.user.dto.UserProfile;
import slightlyspring.imgo.domain.user.dto.UserProfileDetail;
import slightlyspring.imgo.domain.user.repository.UserRepository;

import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Boolean isUserExist(Long userId) {
        return userRepository.existsById(userId);
    }

    public UserProfile getUserProfile(Long userId) {
        User user = userRepository.findById(userId).get();
        UserProfile userProfile = modelMapper.map(user, UserProfile.class);
        return userProfile;
    }

    public UserProfileDetail getUserProfileDetail(Long userId) {
        User user = userRepository.findById(userId).get();
        UserProfileDetail userProfileDetail = modelMapper.map(user, UserProfileDetail.class);
        return userProfileDetail;
    }

}
