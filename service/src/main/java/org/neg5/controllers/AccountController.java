package org.neg5.controllers;

import com.google.inject.Inject;
import neg5.api.AccountApi;
import org.eclipse.jetty.http.HttpStatus;
import org.neg5.AccountDTO;
import org.neg5.auth.LoginAuthenticator;
import org.neg5.auth.LoginCreds;
import org.neg5.core.CurrentUserContext;
import org.neg5.login.DuplicateLoginException;
import org.neg5.AccountCreationDTO;
import org.neg5.util.RequestHelper;
import spark.Request;
import spark.Response;

public class AccountController extends AbstractJsonController {

    private final AccountApi accountManager;
    private final RequestHelper requestHelper;
    private final CurrentUserContext currentUserContext;
    private final LoginAuthenticator loginAuthenticator;

    @Inject
    public AccountController(AccountApi accountManager,
                             RequestHelper requestHelper,
                             CurrentUserContext currentUserContext,
                             LoginAuthenticator loginAuthenticator) {
        this.accountManager = accountManager;
        this.requestHelper = requestHelper;
        this.currentUserContext = currentUserContext;
        this.loginAuthenticator = loginAuthenticator;
    }

    @Override
    protected String getBasePath() {
        return "/neg5-api/accounts";
    }

    @Override
    public void registerRoutes() {
        post("", this::createAccountAndLogin);
        get("/me", (request, response) -> currentUserContext.getUserData().orElse(null));
    }

    private Object createAccountAndLogin(Request request, Response response) {
        try {
            AccountCreationDTO account = requestHelper.readFromRequest(request, AccountCreationDTO.class);
            AccountDTO createdAccount = accountManager.createAccount(account);
            loginAuthenticator.loginByCredentials(buildLoginCreds(account), response);
            return createdAccount;
        } catch (DuplicateLoginException e) {
            response.status(HttpStatus.FORBIDDEN_403);
            return "A login with this username already exists";
        }
    }

    private LoginCreds buildLoginCreds(AccountCreationDTO accountCreationDTO) {
        LoginCreds loginCreds = new LoginCreds();
        loginCreds.setPassword(accountCreationDTO.getPassword());
        loginCreds.setUsername(accountCreationDTO.getUsername());

        return loginCreds;
    }
}
