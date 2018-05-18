import {Component, OnInit} from '@angular/core';
import {ProjectService} from "../project.service";

import {Project} from "../project";

@Component({
    selector: 'app-project',
    templateUrl: './project.component.html',
    styleUrls: ['./project.component.css'],
    providers: [ ProjectService ]
})
export class ProjectComponent implements OnInit {

    projects: Project[];
    colorScheme = {
        domain: ['#5AA454']
    };

    constructor(private projectService: ProjectService) {
    }

    ngOnInit() {
        this.getProjects();
    }

    getProjects(): void {
        this.projectService.getProjects()
            .subscribe(projects => this.projects = projects);
    }
}
