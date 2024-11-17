package doit.shop.repository;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_id")
    private long id;
    private String accountName;
    private String accountNumber;
    private String accountBankName;
    private Integer balance = 0;
    private Long userId;

    @Builder
    private Account(String accountName, String accountNumber, String accountBankName, Integer balance, Long userId) {
        this.accountName = accountName;
        this.accountNumber = accountNumber;
        this.accountBankName = accountBankName;
        this.balance = balance;
        this.userId = userId;
    }
}
