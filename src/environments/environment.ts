// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  uploadFileUrl: 'http://localhost:8080/files?user={0}',
  uploadFileSuccessUrl: 'http://localhost:8080/files/{0}/success?user={1}',
  uploadFileFailureUrl: 'http://localhost:8080/files/{0}/failure?user={1}',
  allFilesUrl: 'http://localhost:8080/files',
  listUsersUrl: 'http://localhost:8080/users',
  deleteUserUrl: 'http://localhost:8080/users/{0}',
  createUserUrl: 'http://localhost:8080/users',
  validateUserUrl: 'http://localhost:8080/users/login'
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
