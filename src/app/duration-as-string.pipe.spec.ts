import { DurationAsStringPipe } from './duration-as-string.pipe';

describe('DurationAsStringPipe', () => {
  it('create an instance', () => {
    const pipe = new DurationAsStringPipe();
    expect(pipe).toBeTruthy();
  });
});
