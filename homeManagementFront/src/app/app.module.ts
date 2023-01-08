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
import { HttpClientModule } from '@angular/common/http';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { AddCatComponent } from './add-cat/add-cat.component';
import {MatGridListModule} from '@angular/material/grid-list';
import { CatMenuComponent } from './components/cat-menu/cat-menu.component';
import { ArticleCardComponent } from './components/article-card/article-card.component';
import {MatToolbarModule}from'@angular/material/toolbar'
import  {MatIconModule} from'@angular/material/icon'
import {MatCardModule} from '@angular/material/card'
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatMenuModule} from '@angular/material/menu';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    AddArticleComponent,
    PageNotFoundComponent,
    AddCatComponent,
    CatMenuComponent,
    ArticleCardComponent,
    
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
    HttpClientModule, 
    MatGridListModule,
    MatToolbarModule,
    MatIconModule,
    MatCardModule,
    MatPaginatorModule,
    MatMenuModule

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
