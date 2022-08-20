import { Location } from "./location-model";

export class Destination {
    constructor(
        public weather: string = '',
        public transportationTypes: string[] = [],
        public destinationTypes: string[] = [],
        public location: Location = new Location(),
        public recommendedTransportationType: string = '',
        public likes: any[] = [],
        public cost: number = 0,
        public grade: number = 0,
        public score: number = 0,
    ) { }
}