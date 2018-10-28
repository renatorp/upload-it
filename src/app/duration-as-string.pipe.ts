import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'durationAsString'
})
export class DurationAsStringPipe implements PipeTransform {

  transform(value: number): any {
    if (value) {
        return this.getValueAsString(value).replace('.', ',');
    }

    return null;
  }
  getValueAsString(value: number): any {
    if (value < 1000) {
      return value + ' ms';
    }
    if (value < 1000 * 60) {
      return value / 1000 + ' s';
    }
    return value / (1000 * 60)  + ' min';
  }

}
