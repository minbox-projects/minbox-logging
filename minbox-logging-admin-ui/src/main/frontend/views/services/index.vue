<!--
  - Copyright 2014-2019 the original author or authors.
  -
  - Licensed under the Apache License, Version 2.0 (the "License");
  - you may not use this file except in compliance with the License.
  - You may obtain a copy of the License at
  -
  -     http://www.apache.org/licenses/LICENSE-2.0
  -
  - Unless required by applicable law or agreed to in writing, software
  - distributed under the License is distributed on an "AS IS" BASIS,
  - WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  - See the License for the specific language governing permissions and
  - limitations under the License.
  -->

<template>
    <div class="section">
        <div class="container">
            <h1 class="title" v-text="$t('services.title')"/>
            <h2
                    v-if="filter.application"
                    v-text="filter.application"
                    class="subtitle"
            />
            <h1
                    v-else-if="filter.instanceId"
                    v-text="`${getName(filter.instanceId)} (${filter.instanceId})`"
                    class="subtitle"
            />
            <table class="journal table is-fullwidth is-hoverable">
                <thead>
                <tr>
                    <th v-text="$t('services.id')"/>
                    <th v-text="$t('services.ip')"/>
                    <th v-text="$t('services.port')"/>
                    <th v-text="$t('services.lastReportTime')"/>
                    <th v-text="$t('services.createTime')"/>
                </tr>
                </thead>
                <transition-group tag="tbody" name="fade-in">
                    <tr key="new-logs" v-if="newServicesCount > 0">
                        <td
                                colspan="6"
                                class="has-text-primary has-text-centered is-selectable"
                                v-text="`${newServicesCount} new logs`"
                                @click="showNewlogs"
                        />
                    </tr>
                    <template v-for="service in listServices">
                        <tr class="is-selectable" :key="service.key">
                            <td v-text="service.id"/>
                            <td v-text="service.ip"/>
                            <td v-text="service.port"/>
                            <td v-text="format(new Date(service.lastReportTime),'yyyy-MM-dd hh:mm:ss')"/>
                            <td v-text="format(new Date(service.createTime),'yyyy-MM-dd hh:mm:ss')"/>
                        </tr>
                    </template>
                </transition-group>
            </table>
        </div>
    </div>
</template>

<script>
    import subscribing from '@/mixins/subscribing';
    import Instance from '@/services/instance';
    import {compareBy} from '@/utils/collections';
    import isEqual from 'lodash/isEqual';
    import uniq from 'lodash/uniq';

    class Service {
        constructor({id, ip, port, lastReportTime, createTime}) {
            this.id = id;
            this.ip = ip;
            this.port = port;
            this.lastReportTime = lastReportTime;
            this.createTime = createTime;
        }

        get key() {
            return `${this.id}`;
        }
    }

    export default {
        mixins: [subscribing],
        data: () => ({
            logs: [],
            listOffset: 0,
            showPayload: {},
            error: null,
            filter: {
                application: undefined,
                instanceId: undefined
            }
        }),
        computed: {
            instanceNames() {
                return this.logs.filter(event => event.type === 'REGISTERED').reduce((names, event) => {
                    names[event.instance] = event.payload.registration.name;
                    return names;
                }, {});
            },
            listServices() {
                return this.filterlogs(this.logs.slice(this.listOffset));
            },
            newServicesCount() {
                return this.filterlogs(this.logs.slice(0, this.listOffset)).length;
            }
        },
        methods: {
            toJson(obj) {
                return JSON.stringify(obj, null, 4);
            },
            getName(instanceId) {
                return this.instanceNames[instanceId] || '?'
            },
            getInstances(application) {
                return uniq(Object.entries(this.instanceNames)
                    .filter(([, name]) => application === name)
                    .map(([instanceId]) => instanceId));
            },
            showNewlogs() {
                this.listOffset = 0;
            },
            filterlogs(logs) {
                return logs;
            },
            createSubscription() {
                /*return Instance.getLogStream().subscribe({
                    next: message => {
                        this.error = null;
                        this.logs = Object.freeze([new Event(message.data), ...this.logs]);
                        this.listOffset += 1;
                    },
                    error: error => {
                        console.warn('Listening for logs failed:', error);
                        this.error = error;
                    }
                });*/
            }, format(date, fmt) {
                let o = {
                    'M+': date.getMonth() + 1, // 月份
                    'd+': date.getDate(), // 日
                    'h+': date.getHours(), // 小时
                    'm+': date.getMinutes(), // 分
                    's+': date.getSeconds(), // 秒
                    'S': date.getMilliseconds() // 毫秒
                }
                if (/(y+)/.test(fmt)) {
                    fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length))
                }
                for (var k in o) {
                    if (new RegExp('(' + k + ')').test(fmt)) {
                        fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (('00' + o[k]).substr(('' + o[k]).length)))
                    }
                }
                return fmt
            }
        },
        watch: {
            '$route.query': {
                immediate: true,
                handler() {
                    this.filter = this.$route.query
                }
            },
            filter: {
                deep: true,
                immediate: true,
                handler() {
                    if (!isEqual(this.filter, this.$route.query)) {
                        this.$router.replace({
                            name: 'logs',
                            query: this.filter
                        });
                    }
                }
            }
        },
        async created() {
            try {
                // get request services
                const response = await Instance.fetchServices();
                const services = response.data.sort(compareBy(v => v.createTime)).reverse().map(e => new Service(e));
                this.logs = Object.freeze(services);
                this.error = null;
            } catch (error) {
                console.warn('Fetching logs failed:', error);
                this.error = error;
            }
        },
        install({viewRegistry}) {
            viewRegistry.addView({
                path: '/services',
                name: 'services',
                label: 'services.top-menu',
                order: 99,
                component: this
            });
        }
    };
</script>
