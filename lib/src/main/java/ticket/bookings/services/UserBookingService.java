package ticket.bookings.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.bookings.entities.User;
import ticket.bookings.utils.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class UserBookingService {

    private User user;

    private List<User> userList;

    private static final String USERS_PATH = "../localDb/users.json";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public UserBookingService(User user) throws IOException {

        this.user = user;

        File users = new File(USERS_PATH);
        userList = objectMapper.readValue(users, new TypeReference<List<User>>() {
        });

    }

        public Boolean loginUser() {
            Optional<User> foundUser = userList.stream().filter(user1 -> {
                return user1.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword());
            }).findFirst();
            return foundUser.isPresent();
        }

        public Boolean signUp(User user1) {
            try {
                userList.add(user1);
                saveUserListToFile();
                return Boolean.TRUE;
            } catch (IOException ex) {
                return Boolean.FALSE;
            }
        }

        private void saveUserListToFile() throws IOException {
            File usersFile = new File(USERS_PATH);
            objectMapper.writeValue(usersFile, userList);
        }

}
