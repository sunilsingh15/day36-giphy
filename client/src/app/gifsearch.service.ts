import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GifsearchService {

  apiURL:string = 'http://localhost:8080/api/gifs';

  constructor(private http: HttpClient) { }

  getGifs(query: string): Observable<any> {
   let queryParam = new HttpParams()
                  .set('search', query);

    return this.http.get(this.apiURL, {params: queryParam});
  }
}
