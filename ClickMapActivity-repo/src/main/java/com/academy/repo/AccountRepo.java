package com.academy.repo;

import com.academy.model.dao.Account;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Daniel Palonek on 2016-08-19.
 */
public interface AccountRepo extends CrudRepository<Account,Long> {

    Account findAccountByUserName(String username);
}
