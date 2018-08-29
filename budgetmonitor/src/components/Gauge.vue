<template>
    <div>
        <svg :width="width" :height="width-100">
            <g :style="'transform: translate('+width/2+'px, '+width/2+'px)'">
                <path :d="emptyArc" :fill="gaugeBackgroundColor"/>
                <path :d="filledArc" :fill="gaugeForegroundColor"/>
                <circle :r="radius" cx="0" cy="0" :fill="gaugeNeedleColor"/>
                <path :d="pointer" :fill="gaugeNeedleColor"/>
                <text x="0" :y="2*radius+width*.05" text-anchor="middle" :font-size="width/15" :fill="gaugeTextColor">{{this.perc*100}}%</text>
            </g>
        </svg>
    </div>
</template>

<script lang="ts">
    import * as d3 from 'd3';
    import {Prop, Component, Vue} from "vue-property-decorator";
    import {Arc, DefaultArcObject} from "d3";

    @Component
    export default class Gauge extends Vue {

        @Prop() private width!: number;
        @Prop() private perc!: number;
        private pointer: string;
        private len: number;
        private radius: number;
        private filledArc: string;
        private emptyArc: string;

        private gaugeBackgroundColor: string;
        private gaugeForegroundColor: string;
        private gaugeNeedleColor: string;
        private gaugeTextColor: string;

        @Prop() private bgColor!: string;
        @Prop() private fgColor!: string;
        @Prop() private needleColor!: string;
        @Prop() private textColor!: string;

        constructor() {
            super();
            this.pointer = '';
            this.filledArc = null;
            this.emptyArc = null;
            this.len = 0;
            this.radius = 0;
        }

        public created(): void {
            this.gaugeBackgroundColor = this.bgColor || '#dedede';
            this.gaugeForegroundColor = this.fgColor || 'steelblue';
            this.gaugeNeedleColor = this.needleColor || '#464A4F';
            this.gaugeTextColor = this.textColor || 'steelblue';
            this.len = this.width / 3;
            this.radius = this.len / 6;
        }

        public mounted(): void {
            this.pointer = this.recalcPointerPos(this.perc);
            let barWidth = 40 * this.width / 300;

            const arcGen: Arc<any,DefaultArcObject> = d3.arc()
                .outerRadius(this.len+2+barWidth)
                .innerRadius(this.len+2);

            this.filledArc = arcGen({
                startAngle: -Math.PI/2,
                endAngle: -Math.PI/2+this.perc*Math.PI
            } as DefaultArcObject);

            this.emptyArc = arcGen({
                startAngle: -Math.PI/2+this.perc*Math.PI+.025,
                endAngle: Math.PI/2
            } as DefaultArcObject);
        }

        private recalcPointerPos(perc: number): string {
            let thetaRad = this.percToRad(perc / 2);
            let centerX = 0;
            let centerY = 0;
            let topX = centerX - this.len * Math.cos(thetaRad);
            let topY = centerY - this.len * Math.sin(thetaRad);
            let leftX = centerX - this.radius * Math.cos(thetaRad - Math.PI / 2);
            let leftY = centerY - this.radius * Math.sin(thetaRad - Math.PI / 2);
            let rightX = centerX - this.radius * Math.cos(thetaRad + Math.PI / 2);
            let rightY = centerY - this.radius * Math.sin(thetaRad + Math.PI / 2);
            return "M " + leftX + " " + leftY + " L " + topX + " " + topY + " L " + rightX + " " + rightY;
        };

        private percToDeg(perc: number): number {
            return perc * 360;
        };

        private percToRad(perc: number): number {
            return this.degToRad(this.percToDeg(perc));
        };

        private degToRad(deg: number): number {
            return deg * Math.PI / 180;
        };
    }
</script>

<style>

</style>