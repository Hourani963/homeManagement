import { Component, OnInit } from '@angular/core';
import { IconSetService } from '@coreui/icons-angular';
import {ArticlesService} from '../../articles.service'
import { Router} from'@angular/router'

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit{
  value = 'Clear me';

  public allArticles = [];

  constructor(private articleService : ArticlesService, private router : Router){}
  ngOnInit(){
    this.allArticles =  this.articleService.getArticles();
    //console.log("%c HEADER", "color:green")
    //console.log(this.allArticles)
  }

  navigateHeader(endPoint:string){
    this.router.navigate([endPoint])
  }

}
