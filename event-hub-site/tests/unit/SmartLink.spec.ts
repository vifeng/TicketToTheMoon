import { mount } from '@vue/test-utils'
import { describe, it, expect } from 'vitest'
import SmartLink from '../../src/components/SmartLink.vue'
import { createRouter, createMemoryHistory } from 'vue-router'

const router = createRouter({
  history: createMemoryHistory(),
  routes: [{ path: '/about', component: { template: '<div>About</div>' } }],
})

describe('SmartLink', () => {
  it('renders RouterLink for internal URLs', async () => {
    router.push('/') // navigate to some initial route
    await router.isReady()

    const wrapper = mount(SmartLink, {
      global: {
        plugins: [router],
      },
      props: {
        to: '/about',
      },
      slots: {
        default: 'About Page',
      },
    })

    expect(wrapper.findComponent({ name: 'RouterLink' }).exists()).toBe(true)
    expect(wrapper.text()).toContain('About Page')
    expect(wrapper.find('[data-testid="external-icon"]').exists()).toBe(false)
  })


  it('renders anchor tag with icon for external URLs', () => {
    const wrapper = mount(SmartLink, {
      props: {
        to: 'https://example.com',
      },
      slots: {
        default: 'External Site',
      },
    })

    const anchor = wrapper.find('a')
    expect(anchor.exists()).toBe(true)
    expect(anchor.attributes('href')).toBe('https://example.com')
    expect(anchor.attributes('target')).toBe('_blank')
    expect(anchor.attributes('rel')).toContain('noopener')
    expect(anchor.text()).toContain('External Site')
expect(wrapper.find('[data-testid="external-icon"]').exists()).toBe(true)
  })
})
