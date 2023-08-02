export class DateUtils {
  formatDate(date: Date): string {
    date = new Date(date);
    if (!(date instanceof Date && !isNaN(date.getTime()))) {
      throw new Error('Invalid Date object');
    }

    const options: Intl.DateTimeFormatOptions = { day: '2-digit', month: '2-digit', year: 'numeric' };
    const formattedDate = date.toLocaleDateString('en-US', options);

    return formattedDate.replace(/\//g, '.');
  }
}
