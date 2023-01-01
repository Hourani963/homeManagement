import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HeaderModule } from '@coreui/angular';
import { IconSetService } from '@coreui/icons-angular';
import { AlertModule } from '@coreui/angular';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { HomeComponent } from './home/home.component';
import { AddArticleComponent } from './add-article/add-article.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatFormFieldModule} from '@angular/material/form-field'
import {MatSelectModule} from '@angular/material/select'
import { MatInputModule } from '@angular/material/input';
import {MatChipsModule} from '@angular/material/chips'
import {MatButtonModule} from '@angular/material/button';
import { UppyAngularDashboardModule } from '@uppy/angular';
import {MatDividerModule} from '@angular/material/divider';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    AddArticleComponent,
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HeaderModule,
    AlertModule,
    BrowserAnimationsModule,
    MatFormFieldModule ,
    MatSelectModule,
    MatInputModule,
    BrowserAnimationsModule,
    MatChipsModule,
    MatButtonModule,
    UppyAngularDashboardModule,
    MatDividerModule,
    FormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
