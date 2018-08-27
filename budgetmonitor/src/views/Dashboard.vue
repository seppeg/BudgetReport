<template>
  <div class="content">
    <div>
      <div class="row">
        <div class="col-lg-4">
          <ProjectCard msg="Java guild"/>
        </div>
        <div class="col-lg-4">
          <ProjectCard msg="Architect guild"/>
        </div>
        <div class="col-lg-4">
          <ProjectCard msg="PM guild"/>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import ProjectCard from '@/components/ProjectCard.vue'; // @ is an alias to /src

@Component({
  components: {
      ProjectCard,
  },
})
export default class Dashboard extends Vue {}
</script>

<style scoped>
  .content {
    padding: 0 30px 30px;
    min-height: calc(100vh - 123px);
  }
</style>
