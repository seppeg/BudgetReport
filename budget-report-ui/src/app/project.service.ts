import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {catchError} from 'rxjs/operators';
import {of} from "rxjs/observable/of";

import {environment} from '../environments/environment';

import {MessageService} from './message.service';
import {Project} from './project';

@Injectable()
export class ProjectService {

    private projectsUrl = environment.projectApiUrl;

    constructor(private http: HttpClient, private messageService: MessageService) {
    }

    getProjects():  Observable<Project[]> {
        return this.http.get<Project[]>(this.projectsUrl)
            .pipe(catchError(this.handleError('getProjects', [])));
    }

    private handleError<T> (operation = 'operation', result?: T) {
        return (error: any): Observable<T> => {
            this.log("problem with operation: " +operation);
            return of(result as T);
        };
    }

    private log(message: string) {
        this.messageService.add('Projects: ' + message);
    }
}