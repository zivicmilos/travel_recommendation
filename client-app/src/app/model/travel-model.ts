import { Destination } from "./destination-model";
import { User } from "./user-model";

export class Travel {
    constructor(
        public user: User = new User(),
        public destination: Destination = new Destination(),
        public travelDate: Date = new Date(),
        public transportationType: string = '',
        public grade: number = 0,
        public cost: number = 0
    ) { }
}