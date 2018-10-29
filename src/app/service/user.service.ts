import { Injectable } from '@angular/core';
import { of, Observable } from 'rxjs';
import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { User } from '../model/user';
import { environment } from 'src/environments/environment';
import { tap, catchError, map } from 'rxjs/operators';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class UserService {
   constructor(private http: HttpClient) { }

  validateUser(user: User): Observable<any> {
    return this.http.post(environment.validateUserUrl, user, httpOptions).pipe(
      tap(_ => this.log(`validating user ` + name))
    );
  }

  public findUsers(): Observable<any> {
    return this.http.get(environment.listUsersUrl, httpOptions).pipe(
      tap(_ => this.log(`find users`))
    );
  }

  public deleteUser(id: string): Observable<any> {
    return this.http.delete(environment.deleteUserUrl.replace('{0}', id), httpOptions).pipe(
      tap(_ => this.log(`deleting user ` + id))
    );
  }

  public createUser(user: User): Observable<any> {
    return this.http.put(environment.createUserUrl, user, httpOptions).pipe(
      tap(_ => this.log(`create new user`))
    );
  }

/** Log a HeroService message with the MessageService */
private log(message: string) {
  console.log(message);
}

}
