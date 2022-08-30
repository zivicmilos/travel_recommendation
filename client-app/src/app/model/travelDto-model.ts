export class TravelDto {
    constructor(
        public user: string = '',
        public destination: string = '',
        public travelDate: Date = new Date(),
        public transportationType: string = '',
        public grade: number = 0,
        public cost: number = 0
    ) { }
}