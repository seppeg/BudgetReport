<template>
    <div class="content">
        <div>
            <div class="row">
                <div class="col-lg-4" v-for="project in projects">
                    <ProjectCard :project="project"/>
                </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
    import {Component, Vue} from 'vue-property-decorator';
    import ProjectCard from '@/components/ProjectCard.vue';
    import {Project} from '../types/project';
    import axios, {AxiosInstance, AxiosResponse} from 'axios';

    @Component({
        components: {
            ProjectCard
        },
    })
    export default class Dashboard extends Vue {
        // private projects: Project[] = [
        //     {
        //         description: 'Java Guild',
        //         workorder: [],
        //         budgets: [{year: 2018, budget: 1000}],
        //         hoursSpent: 890,
        //     },
        //     {
        //         description: 'Architect Guild',
        //         workorder: [],
        //         budgets: [{year: 2018, budget: 1300}],
        //         hoursSpent: 1040,
        //     },
        //     {
        //         description: 'PM Guild',
        //         workorder: [],
        //         budgets: [{year: 2018, budget: 1500}],
        //         hoursSpent: 660,
        //     },
        //     {
        //         description: 'Application Maintenance Guild',
        //         workorder: [],
        //         budgets: [{year: 2018, budget: 900}],
        //         hoursSpent: 333,
        //     },
        // ];

        public projects: Project[] = [];
        private url: string = 'http://project.localhost/project';
        axios: AxiosInstance;

        constructor() {
            super();
            this.axios = axios;
        }

        public created(): void {
            this.fetchData();
        }

        private fetchData(): void {
            if (this.projects.length) {
                return;
            }

            this.axios.get(this.url)
                .then((response: AxiosResponse) => {
                    this.projects = response.data;
                })
                .catch((error: Error) => {
                    console.error(error);
                });
        }
    }
</script>

<style scoped>
    .content {
        padding: 0 30px 30px;
        min-height: calc(100vh - 123px);
    }
</style>
