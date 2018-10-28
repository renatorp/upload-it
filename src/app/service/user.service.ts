import { Injectable } from '@angular/core';
import { of, Observable } from 'rxjs';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { User } from '../model/user';
import { environment } from 'src/environments/environment';
import { tap, catchError } from 'rxjs/operators';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  public findUsers(): Observable<User[]> {
    return this.http.get(environment.listUsersUrl, httpOptions).pipe(
      tap(_ => this.log(`find users`)),
      catchError(this.handleError<any>('findUsers'))
    );
  }

  public deleteUser(id: string): Observable<any> {
    return this.http.delete(environment.deleteUserUrl.replace('{0}', id), httpOptions).pipe(
      tap(_ => this.log(`deleting user ` + id)),
      catchError(this.handleError<any>('deleteUser'))
    );
  }

  public createUser(user: User): Observable<any> {
    return this.http.put(environment.createUserUrl, user, httpOptions).pipe(
      tap(_ => this.log(`create new user`)),
      catchError(this.handleError<any>('createUser'))
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
