import { Routes, RouterModule } from '@angular/router';
import { DragonComponent } from './dragon.component';
import {ProjectComponent} from "./project/project.component";

const appRoutes: Routes = [
    { path: '', component: ProjectComponent },
    { path: 'xan', component: DragonComponent }
];

export const routing = RouterModule.forRoot(appRoutes);