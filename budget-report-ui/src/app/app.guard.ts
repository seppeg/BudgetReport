import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';

@Injectable()
export class AuthGuard implements CanActivate {

    constructor(private router: Router) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        let token = localStorage.getItem('token');
        if (typeof  token === 'undefined' || token === null || token === 'undefined') {
            this.router.navigate(['/login'], { queryParams: { returnUrl: state.url }});
            return false;
        }

        return true;
    }
}