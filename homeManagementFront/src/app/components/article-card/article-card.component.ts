import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Router} from '@angular/router'
import {articleUrl} from '../../apiUrls'


@Component({
  selector: 'app-article-card',
  templateUrl: './article-card.component.html',
  styleUrls: ['./article-card.component.scss']
})
export class ArticleCardComponent implements OnInit{
  public ARICLE_URL = articleUrl;

  public allArticles: any = [];


  constructor(private router : Router,private http: HttpClient){}


  ngOnInit(){
    this.http.get<any>(articleUrl).subscribe(data => {
            this.allArticles.push(data)
            
            console.log(this.allArticles)
    })
  }


  
}