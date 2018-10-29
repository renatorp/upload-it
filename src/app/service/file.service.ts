import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable, of } from 'rxjs';
import { catchError, tap, map } from 'rxjs/operators';
import { FileUpload } from '../model/file-upload';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class FileService {
  constructor(private http: HttpClient) { }

  public reportUploadFailure(fileName: string, userId: string): Observable<any> {

    const url = environment.uploadFileFailureUrl
    .replace('{0}', fileName)
    .replace('{1}', userId);

    return this.http.patch(url, httpOptions).pipe(
      tap(_ => this.log(`report failure flie id=${fileName}`))
    );
  }


  public reportUploadSuccess(fileName: string, userId: string): Observable<any> {
    const url = environment.uploadFileSuccessUrl
      .replace('{0}', fileName)
      .replace('{1}', userId);

    return this.http.patch(url, httpOptions).pipe(
      tap(_ => this.log(`report success flie id=${fileName}`))
    );
  }

  public findAllUploadedFiles(): Observable<FileUpload[]> {
    return this.http.get(environment.allFilesUrl, httpOptions).pipe(
      tap(_ => this.log(`find all uploaded files`)),
      map(r => <FileUpload[]> r)
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
