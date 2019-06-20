#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
# See the NOTICE file distributed with this work for additional
# information regarding copyright ownership.
#

FROM #BASE_IMAGE

#Sampling rate of the profiler in Hz (or per s)
ARG PROFILER_FREQ 
RUN echo "$PROFILER_FREQ" > /.profiler

#PERF
RUN apt-get update && apt-get install -y build-essential cmake wget unzip flex bison && rm -rf /var/lib/apt/lists/*
#PERF_EXTRA
RUN wget https://github.com/torvalds/linux/archive/v5.1.zip && unzip v5.1.zip && rm v5.1.zip && \
    cd linux-5.1/tools/perf && make && cp perf /usr/bin && \
    rm -rf linux-5.1

#COPY

#DOCKER_INSTRUCTION

#EXPOSE

CMD ["/bin/sh", "run.sh"]