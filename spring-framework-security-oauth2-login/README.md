OAuth2 is an open standard used to allow entities like users to grant limited access to their resources, without sharing their credential.

Principle of least knowledge is limiting access rights to the bare minimum permissions required by the subject.

OAuth 2 is not even designed for authentication. It's actually designed for delegated authorization. OAuth 2 spec simply mentions authentication should happen, but doesn't mention how.  
We can use own implementation on top of OAuth to support authentication, like OpenID Connect.  
OpenID Connect is an identity layer on top of the OAuth 2.0 protocol.

[Spring](https://github.com/spring-projects/spring-security/wiki/OAuth-2.0-Features-Matrix) take care about [OAuth 2.1](https://oauth.net/2.1/) [complexity](https://developer.okta.com/blog/2019/12/13/oauth-2-1-how-many-rfcs)