import { Component, OnInit, Input } from '@angular/core';
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

  constructor(private router : Router,private http: HttpClient){}


  @Input('parentData') public article:any;

  
  ngOnInit(){
    
  }


  
}
