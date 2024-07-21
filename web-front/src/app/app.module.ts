import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { FormsModule } from "@angular/forms";
import { HttpClientModule } from "@angular/common/http";

import { AppRoutingModule } from "./app-routing.modules";
import { AppComponent } from "./app.component";
import { LoginComponent } from "./Components/login/login.component";
import { RegisterComponent } from "./Components/register/register.component";
import { HomeComponent } from "./Components/home/home.component";
import { ProfileComponent } from "./Components/profile/profile.component";
import { AnnotationAllviewComponent } from "./Components/annotation-allview/annotation-allview.component";
import { AnnotationCreationComponent } from "./Components/annotation-creation/annotation-creation.component";
import { HttpInterceptorProviders } from "./Services&Helpers/_interceptor/http.interceptor";
import { CommonModule } from "@angular/common";


@NgModule({
    declarations: [
        AppComponent,
        LoginComponent,
        RegisterComponent,
        HomeComponent,
        ProfileComponent,
        AnnotationAllviewComponent,
        AnnotationCreationComponent
    ],
    imports: [
        BrowserModule,
        CommonModule,
        AppRoutingModule,
        FormsModule,
        HttpClientModule
    ],
    providers : [HttpInterceptorProviders],
    bootstrap : [AppComponent]
})

export class AppModule { }