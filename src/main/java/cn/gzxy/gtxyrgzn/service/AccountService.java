package cn.gzxy.gtxyrgzn.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import cn.gzxy.gtxyrgzn.data.AccountDto;
import cn.gzxy.gtxyrgzn.repository.AccountRepository;
import cn.gzxy.gtxyrgzn.utils.RandomUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AccountService {
    final AccountRepository accountRepository;

    public AccountDto.Account account() {
        return null;
    }

    public AccountDto.Account login(AccountDto.Login login) {
        cn.gzxy.gtxyrgzn.model.Account account = accountRepository.findByUsername(login.username());
        if (!BCrypt.verifyer().verify(login.password().toCharArray(), account.password()).verified) return null;

        return new AccountDto.Account(account.username());
    }

    public AccountDto.Account register(AccountDto.Register register) {
        String password = BCrypt.withDefaults().hashToString(12, register.password().toCharArray());

        cn.gzxy.gtxyrgzn.model.Account account = new cn.gzxy.gtxyrgzn.model.Account(
                RandomUtils.uuid(),
                null,
                register.username(),
                password,
                new ArrayList<>(),
                new ArrayList<>()
        );

        cn.gzxy.gtxyrgzn.model.Account save = accountRepository.save(account);
        return new AccountDto.Account(save.username());
    }
}
