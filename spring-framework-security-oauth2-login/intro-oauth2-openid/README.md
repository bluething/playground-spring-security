![oauth2actors](https://github.com/bluething/playground-spring-security/blob/main/spring-framework-security-oauth2-login/images/oauthactors.png?raw=true)  
- Resource Owner. Entity that can grant access to a protected resource.  
- Client. Application requesting access to a protected resource on behalf of the Resource Owner.  
- Resource Server. Server hosting the protected resources.  
- Authorization Server: Server that authenticates the Resource Owner and issues Access Tokens after getting proper authorization.

What if the token use by stranger? Sign the token.

The OAuth 2.0 Authorization Framework supports several flows (or grants). Flows are ways of retrieving an Access Token.  
1. Authorization Code.  
2. PKCE.  
3. Client Credentials.  
4. Device Code.  
5. Refresh Token.

Authorization code flow (credit image by ebay)  
![authorization code](https://github.com/bluething/playground-spring-security/blob/main/spring-framework-security-oauth2-login/images/authorization_code_flow.png?raw=true)

Spring Security support OAuth provider in org.springframework.security.config.oauth2.client.CommonOAuth2Provider  
```java
    GOOGLE {
        public Builder getBuilder(String registrationId) {
            Builder builder = this.getBuilder(registrationId, ClientAuthenticationMethod.CLIENT_SECRET_BASIC, "{baseUrl}/{action}/oauth2/code/{registrationId}");
            builder.scope(new String[]{"openid", "profile", "email"});
            builder.authorizationUri("https://accounts.google.com/o/oauth2/v2/auth");
            builder.tokenUri("https://www.googleapis.com/oauth2/v4/token");
            builder.jwkSetUri("https://www.googleapis.com/oauth2/v3/certs");
            builder.issuerUri("https://accounts.google.com");
            builder.userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo");
            builder.userNameAttributeName("sub");
            builder.clientName("Google");
            return builder;
        }
    }
```

Case login with GitHub  
1. Open http://localhost:8080/portfolio  
2. The browser will redirect to GitHub with GET request  
   https://github.com/login/oauth/authorize?response_type=code&client_id=${our_client_id}&scope=read:user&state=r4oNebc2is5ZDQfou4KM336lRzPWz_Nr7JIgx8jVDDc%3D&redirect_uri=http://localhost:8080/login/oauth2/code/github  
3. Login to GitHub.  
![GitHub login](https://github.com/bluething/playground-spring-security/blob/main/spring-framework-security-oauth2-login/images/githublogin.png?raw=true)
4. GitHub will ask us to authorize the app.  
![GitHub authorize](https://github.com/bluething/playground-spring-security/blob/main/spring-framework-security-oauth2-login/images/githubauthorize.png?raw=true)
5. GitHub redirect us back to the portfolio app with request  
   http://localhost:8080/login/oauth2/code/github?code=f1da8411fe9082495d&state=r4oNebc2is5ZDQfou4KM336lRzPWz_Nr7JIgx8jVDDc%3D

code: authorization code (not an access token)
state: use for csrf prevention.

How to get access token? Using back channel request.  
Request to authorization server using authorization code + client id + client secret + https.  
Spring Security is making direct calls, ideally in production over TLS, to GitHub's token URI  
```java
builder.tokenUri("https://github.com/login/oauth/access_token");
```  
We can see the access token value, put breakpoint in org.springframework.security.oauth2.client.authentication.OAuth2AuthorizationCodeAuthenticationProvider.

OAuth2 -> Delegated authorization  
OpenID Connect -> Authentication layer

OpenID Connect:  
- Provides an identity layer on top of the OAuth 2.0 protocol.  
- Clients can verify the identity of an end-user based on the authentication performed by an authorization server.  
- Give client access to basic profile information about the end user via REST.

Use case OpenID Connect with Google  
1. Create OAuth 2.0 Client IDs, for first time we need to create consent screen.  
2. The form similar with GitHub case.  
3. After we add client id and client secret to application.properties, restart the app.  
4. We will prompt with login options.  
![login options](https://github.com/bluething/playground-spring-security/blob/main/spring-framework-security-oauth2-login/images/loginoptions.png?raw=true)
5. We can see request url to google contain scope=openid  
   https://accounts.google.com/o/oauth2/v2/auth?response_type=code&client_id=${client_id}&scope=openid%20profile%20email&state=pBJ02tyQNGzfi6QZNVrwyqsWKEuaiRlsPoJd4wYhTL8%3D&redirect_uri=http://localhost:8080/login/oauth2/code/google&nonce=pSVM61nWEbvCS8eDc7eGsXrT4CJwTsdTaOsCg0_oaGY  
6. Login to Google, then we redirect back to app with request  
   http://localhost:8080/login/oauth2/code/google?state=pBJ02tyQNGzfi6QZNVrwyqsWKEuaiRlsPoJd4wYhTL8%3D&code=4%2F0AX4XfWixr8PVSlUgOpSvD_ie71N5HOgruszFG36hHjeElqP2ZM3XNfy1me4x7z3i_7qQ&scope=email+profile+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=consent

[Google OpenID configuration](https://accounts.google.com/.well-known/openid-configuration)

OpenID Connect token  
- ID Token. Used for authentication by the client.  
- Access token. Used for authorization by the resource server.

We should prefer OpenID Connect for user authentication scenarios.

### Read more

[An Introduction to OAuth 2](https://www.digitalocean.com/community/tutorials/an-introduction-to-oauth-2)  
[OAuth Grant Types](https://oauth.net/2/grant-types/)  
[Which OAuth 2.0 Flow Should I Use?](https://auth0.com/docs/get-started/authentication-and-authorization-flow/which-oauth-2-0-flow-should-i-use)  
[Welcome to OpenID Connect](https://openid.net/connect/)