import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private http: HttpClient) { }

  public reportUploadFailure(fileId: string): Observable<any> {
    const url = environment.uploadFileFailureUrl.replace('{0}', fileId);

    return this.http.patch(url, httpOptions).pipe(
      tap(_ => this.log(`report failure flie id=${fileId}`)),
      catchError(this.handleError<any>('reportUploadFailure'))
    );
  }


  public reportUploadSuccess(fileId: string): Observable<any> {
    const url = environment.uploadFileSuccessUrl.replace('{0}', fileId);

    return this.http.patch(url, httpOptions).pipe(
      tap(_ => this.log(`report success flie id=${fileId}`)),
      catchError(this.handleError<any>('reportUploadSuccess'))
    );
  }


/**
 * Handle Http operation that failed.
 * Let the app continue.
 * @param operation - name of the operation that failed
 * @param result - optional value to return as the observable result
 */
private handleError<T> (operation = 'operation', result?: T) {
  return (error: any): Observable<T> => {

    this.log(`${operation} failed: ${error.message}`);

    // Let the app keep running by returning an empty result.
    return of(result as T);
  };
}

/** Log a HeroService message with the MessageService */
private log(message: string) {
  console.log(message);
}


}
