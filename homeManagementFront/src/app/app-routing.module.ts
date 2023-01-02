import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddArticleComponent } from './add-article/add-article.component';
import { AddCatComponent } from './add-cat/add-cat.component';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';

const routes: Routes = [
  {path : '', redirectTo:'home',pathMatch:'full'},
  {path : 'home',component : HomeComponent},
  {path : 'addArticle',component : AddArticleComponent},
  {path : 'addCat',component : AddCatComponent},
  {path : '**',component : PageNotFoundComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
