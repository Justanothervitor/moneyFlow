import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";

import { RegisterComponent } from "./register/register.component";
import { LoginComponent } from "./login/login.component";
import { HomeComponent } from "./home/home.component";
import { ProfileComponent } from "./profile/profile.component";
import { AnnotationAllviewComponent } from "./annotation-allview/annotation-allview.component";
import { AnnotationCreationComponent } from "./annotation-creation/annotation-creation.component";

const routes: Routes = [

    {path : 'home', component:HomeComponent},
    {path : 'login',component:LoginComponent},
    {path : 'register',component:RegisterComponent},
    {path : 'profile',component:ProfileComponent},
    {path : 'annotations',component:AnnotationAllviewComponent},
    {path : 'create',component:AnnotationCreationComponent},
    {path : '',redirectTo: 'home', pathMatch: 'full'}
];

@NgModule({
    imports:[RouterModule.forRoot(routes)],
    exports:[RouterModule]
})

export class AppRoutingModule { }