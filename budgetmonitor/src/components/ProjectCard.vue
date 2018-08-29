<template>
    <div class="card">
        <div class="card-header">
            <h2 class="card-title">{{project.description}}</h2>
        </div>
        <div class="card-body">
            <Gauge :width="300" :perc="(project.hoursSpent)/(project.budgets[0].budget)"/>
        </div>
        <div class="card-footer">
            <span><img src="../assets/clock.png" width="20px"/> {{project.hoursSpent}}/{{project.budgets[0].budget}}</span>
        </div>
    </div>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import Gauge from '@/components/Gauge.vue';
    import {Project} from "../types/project";

    @Component({
        components: {
            Gauge
        }
    })
    export default class ProjectCard extends Vue {
        @Prop() private project!: Project;
    }
</script>

<style scoped>

    .card {
        border: 0;
        border-radius: .1875rem;
        display: inline-block;
        position: relative;
        width: 100%;
        margin-bottom: 20px;
        box-shadow: 0 1px 15px 1px rgba(36, 36, 36, .1);
    }

    .card .card-header:not([data-background-color]) {
        background-color: transparent;
    }

    .card .card-header {
        padding: 15px 15px 0;
        border: 0;
    }

    .card-title {
        margin-top: 10px;
        margin-bottom: 0;
    }
</style>
