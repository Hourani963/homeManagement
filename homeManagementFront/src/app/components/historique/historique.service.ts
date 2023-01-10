import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {histoUrl} from '../../apiUrls'

@Injectable({
  providedIn: 'root'
})
export class HistoriqueService {

  constructor(private http:HttpClient) { }


  getHistorique():Observable<any> {
    return this.http.get(histoUrl)
  }
}
