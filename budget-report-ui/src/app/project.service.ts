import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';

import {Project} from './project';

@Injectable()
export class ProjectService {

    private projectsUrl = 'api/projects';

    constructor(private http: HttpClient) {
    }

    getProjects():  Observable<Project[]> {
        return this.http.get<Project[]>(this.projectsUrl);
    }
}