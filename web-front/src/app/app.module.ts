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
import { HttpInterceptorProviders } from "./ServicesAndHelpers/_interceptor/http.interceptor";
import { CommonModule } from "@angular/common";
import { AnnotationViewComponent } from "./Components/annotation-view/annotation-view.component";
import {LoadingComponent} from "./Components/loading/loading.component";
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';




@NgModule({
    declarations: [
        AppComponent,
        LoginComponent,
        RegisterComponent,
        HomeComponent,
        ProfileComponent,
        AnnotationAllviewComponent,
        AnnotationCreationComponent,
        AnnotationViewComponent,
    ],
    imports: [
        BrowserModule,
        CommonModule,
        AppRoutingModule,
        FormsModule,
        HttpClientModule,
        LoadingComponent
    ],
    providers : [HttpInterceptorProviders,provideAnimationsAsync()],
    bootstrap : [AppComponent]
})

export class AppModule { }
