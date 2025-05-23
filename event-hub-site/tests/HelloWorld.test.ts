// tests/HelloWorld.test.ts
import { describe, it, expect } from 'vitest'
import { mount } from '@vue/test-utils'
import HelloWorld from '../src/components/HelloWorld.vue'

describe('HelloWorld.vue', () => {
  it('renders the message', () => {
    const wrapper = mount(HelloWorld, {
      props: { msg: 'Hello Vitest' }
    })
    expect(wrapper.text()).toContain('Hello Vitest')
  })

  it('increments count when button is clicked', async () => {
    const wrapper = mount(HelloWorld, {
      props: { msg: 'Click test' }
    })
    const button = wrapper.find('button')
    await button.trigger('click')
    expect(button.text()).toContain('1')
  })
})
