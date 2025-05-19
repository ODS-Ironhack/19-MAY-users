package com.ironhack.users_micro.dto;

import com.ironhack.users_micro.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String username;
    private Long accountID;
    private AccountDTO account;
    public UserResponseDTO(User user, AccountDTO account){
        setId(user.getId());
        setUsername(user.getUsername());
        setAccountID(user.getAccountID());
        setAccount(account);
    }

}
