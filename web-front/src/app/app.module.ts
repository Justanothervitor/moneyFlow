import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { FormsModule } from "@angular/forms";
import { HttpClientModule } from "@angular/common/http";

import { AppRoutingModule } from "./app-routing.modules";
import { AppComponent } from "./app.component";
import { LoginComponent } from "./login/login.component";
import { RegisterComponent } from "./register/register.component";
import { HomeComponent } from "./home/home.component";
import { ProfileComponent } from "./profile/profile.component";
import { AnnotationAllviewComponent } from "./annotation-allview/annotation-allview.component";
import { AnnotationCreationComponent } from "./annotation-creation/annotation-creation.component";
import { HttpInterceptorProviders } from "./_interceptor/http.interceptor";

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
        AppRoutingModule,
        FormsModule,
        HttpClientModule
    ],
    providers : [HttpInterceptorProviders],
    bootstrap : [AppComponent]
})

export class AppModule { }