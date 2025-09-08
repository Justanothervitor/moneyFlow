import { Injectable } from '@angular/core';
import { HttpEvent,HttpInterceptor,HttpHandler,HttpRequest,HTTP_INTERCEPTORS } from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from "../../../environments/environment";
import {StorageService} from "../_services/storage.service";

const httpOptions = environment.httpOptions;

@Injectable()
export class HttpRequestInterceptor implements HttpInterceptor{


    intercept(req: HttpRequest<any>,next:HttpHandler):Observable<HttpEvent<any>>{
        req = req.clone({
            withCredentials : true,
            setHeaders : {
              'Accept': 'application/json',
              'Content-Type' : 'application/json',
              'Authorization' : `Bearer ${localStorage.getItem('Authorization')}`,
            }
        });

        return next.handle(req);
    }
}

export const HttpInterceptorProviders =[
    {provide: HTTP_INTERCEPTORS,useClass: HttpRequestInterceptor,multi:true},
];
