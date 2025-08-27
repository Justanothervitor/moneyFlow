import { NgModule} from "@angular/core";
import { Routes,RouterModule, withComponentInputBinding } from "@angular/router";

import { LoginComponent } from "./Components/login/login.component";
import { RegisterComponent } from "./Components/register/register.component";
import { HomeComponent } from "./Components/home/home.component";
import { ProfileComponent } from "./Components/profile/profile.component";
import { AnnotationAllviewComponent } from "./Components/annotation-allview/annotation-allview.component";
import { AnnotationCreationComponent } from "./Components/annotation-creation/annotation-creation.component";
import { AnnotationViewComponent } from "./Components/annotation-view/annotation-view.component";

const routes: Routes = [

    {path : 'home', component:HomeComponent},
    {path : 'login',component:LoginComponent},
    {path : 'register',component:RegisterComponent},
    {path : 'profile',component:ProfileComponent},
    {path : 'annotations',component:AnnotationAllviewComponent},
    {path : 'create',component:AnnotationCreationComponent},
    {path : 'annotations/:id',component:AnnotationViewComponent},
    {path : '',redirectTo: 'home', pathMatch: 'full'}
];

@NgModule({
    imports:[RouterModule.forRoot(routes)],
    exports:[RouterModule]
})
export class AppRoutingModule { }
