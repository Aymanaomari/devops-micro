import {HttpInterceptorFn} from "@angular/common/http";
import {inject} from "@angular/core";
import {OidcSecurityService} from "angular-auth-oidc-client";
import {switchMap} from "rxjs/operators";

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(OidcSecurityService);

  return authService.getAccessToken().pipe(
    switchMap(token => {
      let headers = req.headers;

      if (token) {
        headers = headers.set('Authorization', `Bearer ${token}`);
      }

      const authenticatedReq = req.clone({ headers, withCredentials: true });
      return next(authenticatedReq);
    })
  );
};